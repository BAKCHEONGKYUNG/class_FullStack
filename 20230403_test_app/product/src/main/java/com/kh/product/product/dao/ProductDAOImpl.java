package com.kh.product.product.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

    private final NamedParameterJdbcTemplate template;
    /**
     * @param product 상품
     * @return 상품아이디
     */
    @Override
    public Long save(Product product) {

        StringBuffer sb = new StringBuffer();
        sb.append("insert into product_test(pid, pname, quantity, price) ");
        sb.append("values(product_test_pid_seq.nextval, :pname, :quantity, :price) ");

        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sb.toString(), param, keyHolder, new String[]{"pid"});

        Long pid = keyHolder.getKey().longValue();

        return pid;
    }

    /**
     * @param pid
     * @return
     */
    @Override
    public Optional<Product> findById(Long pid) {

        StringBuffer sb = new StringBuffer();
        sb.append("select pid, pname, quantity, price ");
        sb.append("  from product_test ");
        sb.append(" where pid = :pid ");

        try {
            Map<String, Long> param = Map.of("pid", pid);

            Product product = template.queryForObject(
                    sb.toString(), param, productRowMapper());
                return Optional.of(product);

        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    //수동매핑
    private RowMapper<Product> productRowMapper() {

        return (rs, rowNum) -> {
            Product product = new Product();

            product.setPid(rs.getLong("pid"));
            product.setPname(rs.getString("pname"));
            product.setQuantity(rs.getLong("quantity"));
            product.setPrice(rs.getLong("price"));

            return product;
        };
    }

    /**
     * @param pid
     * @param product
     * @return
     */
    @Override
    public int update(Long pid, Product product) {

        StringBuffer sb = new StringBuffer();
        sb.append("update product_test ");
        sb.append("   set pname = :pname ");
        sb.append("       quantity = :quantity ");
        sb.append("       price = :price ");
        sb.append(" where pid = :pid ");

        //Map 방식
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("pname", product.getPname())
                .addValue("quantity", product.getQuantity())
                .addValue("price", product.getPrice())
                .addValue("pid", pid);

        return template.update(sb.toString(), param);
    }

    /**
     * @param pid
     * @return
     */
    @Override
    public int delete(Long pid) {

        String sql = "delete from product_test where pid = :pid ";

        return template.update(sql, Map.of("pid", pid));
    }

    /**
     * 부분삭제 *
     * @param pids
     * @return
     */
    @Override
    public int deleteParts(List<Long> pids) {

        String sql = "delete from product_test where pid in ( :ids ) ";

        Map<String, List<Long>> param = Map.of("ids", pids);
        return template.update(sql, param);
    }

    /**
     * 전체삭제
     * @return
     */
    @Override
    public int deleteAll() {

        String sql = "delete from product_test ";

        Map<String, String> param = new LinkedHashMap<>();

        int deletedRowCnt = template.update(sql, param);

        return deletedRowCnt;
    }

    /**
     * 상품목록
     * @return
     */
    @Override
    public List<Product> findAll() {

        StringBuffer sb = new StringBuffer();
        sb.append("select pid, pname, quantity, price ");
        sb.append("  from product_test ");

        //여러행 조회
        List<Product> list = template.query(
                sb.toString(),
                BeanPropertyRowMapper.newInstance(Product.class)
        );

        return list;
    }

    class RowMapperImpl implements RowMapper<Product> {

        /**
         * Implementations must implement this method to map each row of data in the
         * {@code ResultSet}. This method should not call {@code next()} on the
         * {@code ResultSet}; it is only supposed to map values of the current row.
         *
         * @param rs     the {@code ResultSet} to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if an SQLException is encountered while getting
         *                      column values (that is, there's no need to catch SQLException)
         */
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

            Product product = new Product();
            product.setPid(rs.getLong("pid"));
            product.setPname(rs.getString("pname"));
            product.setQuantity(rs.getLong("quantity"));
            product.setPrice(rs.getLong("price"));

            return product;
        }
    }

    /**
     * 상품의 존재유무
     * @param pid 상품아이디
     * @return 상품
     */
    @Override
    public boolean isExist(Long pid){

        boolean isExist = false;

        String sql = "select count(*) from product_test where pid = :pid ";

        //단일 값 구하기
        Map<String, Long> param = Map.of("pid", pid);

        Integer integer = template.queryForObject(sql, param, Integer.class);

        isExist = (integer > 0) ? true : false;

        return isExist;
    }

    /**
     * 등록된 상품건수
     * @return 레코드 건수
     */
    @Override
    public int countOfRecord() {

        String sql = "select count(*) from product_test ";

        Map<String, String> param = new LinkedHashMap<>();

        Integer rows = template.queryForObject(sql, param, Integer.class);

        return rows;
    }
}
