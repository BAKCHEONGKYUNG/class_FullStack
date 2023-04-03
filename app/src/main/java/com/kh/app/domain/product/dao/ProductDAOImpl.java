package com.kh.app.domain.product.dao;

import com.kh.app.domain.entity.Product;
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
@Repository //테스트코드 실행 시 인스턴스화를 해준다.
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

    //얘 객체에 CRUD 를 정의
    private final NamedParameterJdbcTemplate template;

    /**
     * 등록
     *
     * @param product 상품
     * @return 상품아이디
     */
    @Override
    public Long save(Product product) {
        //StringBuffer 객체를 이용해 문자열 + 새로운 문자열
        //데이터를 적게 잡아 쓴다.
        StringBuffer sb = new StringBuffer();
        sb.append("insert into product(product_id,pname,quantity,price) ");
        sb.append("values(product_product_id_seq.nextval, :pname, :quantity, :price) ");

        //파라미터 반영하는 방법 3가지, 매개값으로 product/ BeanPropertySqlParameterSource
        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        //KeyHolder 객체 사용하여 insert된 특정값을 불러올 때 사용. ex)product_id 사용을 위해
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //update라는 메소드를 사용할땐, 1)쿼리, 2)바꿔지는 값(파라미터), 3)KeyHolder, 4)KeyHolder에 담을 컬럼명
        template.update(sb.toString(),param,keyHolder,new String[]{"product_id"});
//    template.update(sb.toString(),param,keyHolder,new String[]{"product_id","pname"});

        //product_id 값을 들고 오기 위해 사용, (기존 Number 타입)
        long productId = keyHolder.getKey().longValue(); //상품아이디

        //String pname = (String)keyHolder.getKeys().get("pname");
        return productId;
    }

    /**
     * 조회
     *
     * @param productId 상품아이디
     * @return 상품
     */
    @Override
    public Optional<Product> findById(Long productId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select product_id, pname, quantity, price ");
        sb.append("  from product ");
        sb.append(" where product_id = :id ");

        try {
            Map<String, Long> param = Map.of("id", productId);

            Product product = template.queryForObject(
                    sb.toString(), param, productRowMapper());
            return Optional.of(product);
            //Exception 발생
        } catch (EmptyResultDataAccessException e) {
            //조회결과가 없는 경우
            return Optional.empty();
        }
    }

    /**
     * 수정
     *
     * @param productId 상품아이디
     * @param product   상품
     * @return 수정된 레코드 수
     */
    @Override
    public int update(Long productId, Product product) {
        StringBuffer sb = new StringBuffer();
        sb.append("update product ");
        sb.append("   set pname = :pname, ");
        sb.append("       quantity = :quantity, ");
        sb.append("       price = :price ");
        sb.append(" where product_id = :id ");

        //Map 방식으로
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("pname",product.getPname())
                .addValue("quantity",product.getQuantity())
                .addValue("price",product.getPrice())
                .addValue("id",productId);

        //update 메소드를 사용해서, 삭제 수정
        return template.update(sb.toString(),param);
    }

    /**
     * 삭제
     *
     * @param productId 상품아이디
     * @return 삭제된 레코드 수
     */
    @Override
    public int delete(Long productId) {
        String sql = "delete from product where product_id = :id ";
        //파라미터가 하나이기때문에
        return template.update(sql,Map.of("id",productId));
    }

    /**
     * 부분삭제
     *
     * @param productIds
     * @return
     */
    @Override
    public int deleteParts(List<Long> productIds) {
        String sql = "delete from product where product_id in ( :ids ) ";
        Map<String, List<Long>> param = Map.of("ids", productIds);
        return template.update(sql, param);
    }

    /**
     * 전체 삭제
     *
     * @return
     */
    @Override
    public int deleteAll() {
        String sql = "delete from product ";
        Map<String, String> param = new LinkedHashMap<>();
        int deletedRowCnt = template.update(sql, param);
        return deletedRowCnt;
    }

    /**
     * 목록
     *
     * @return 상품목록
     */
    @Override
    //자동 매핑 형식
    public List<Product> findAll() {
        StringBuffer sb = new StringBuffer();
        sb.append("select product_id, pname, quantity, price ");
        sb.append("  from product ");

        //여러행 조회시 query
        List<Product> list = template.query(
                sb.toString(),
                //Product.class, 레코드를 담을 타입 product_id, pname, quantity, price
                // 레코드 컬럼과 자바객체 멤버필드가 동일한 이름일경우, camelcase지원
                BeanPropertyRowMapper.newInstance(Product.class)
        );

        return list;
    }

    class RowMapperImpl implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setProductId(rs.getLong("product_id"));
            product.setPname(rs.getString("pname"));
            product.setQuantity(rs.getLong("quantity"));
            product.setPrice(rs.getLong("price"));
            return product;
        }
    }

//  RowMapper<Product> rowMapper = new RowMapper<Product>() {
//    @Override
//    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
//      Product product = new Product();
//      product.setProductId(rs.getLong("product_id"));
//      product.setPname(rs.getString("pname"));
//      product.setQuantity(rs.getLong("quantity"));
//      product.setPrice(rs.getLong("price"));
//      return product;
//    }
//  };
//
//  RowMapper<Product> rowMapper2 = (rs, rowNum) -> {
//      Product product = new Product();
//      product.setProductId(rs.getLong("product_id"));
//      product.setPname(rs.getString("pname"));
//      product.setQuantity(rs.getLong("quantity"));
//      product.setPrice(rs.getLong("price"));
//      return product;
//  };

    //수동 매핑
    private RowMapper<Product> productRowMapper() {
        //rs -> 테이블 레코드 결과
        return (rs, rowNum) -> {
            Product product = new Product();
            //컬럼이름
            product.setProductId(rs.getLong("product_id"));
            product.setPname(rs.getString("pname"));
            product.setQuantity(rs.getLong("quantity"));
            product.setPrice(rs.getLong("price"));
            return product;
        };
    }

    //자동매핑 : 테이블의 컬럼명과 자바객체 타입의 멤버필드가 같아야한다.
    // BeanPropertyRowMapper.newInstance(자바객체타입)


    /**
     * 상품의 존재유무
     * @param productId 상품아이디
     * @return 상품
     */
    @Override
    public boolean isExist(Long productId) {
        boolean isExist = false;
        String sql = "select count(*) from product where product_id = :product_id ";

        //단일 값 구하기
        Map<String, Long> param = Map.of("product_id", productId);
        //3)단일행, 단일 열
        Integer integer = template.queryForObject(sql, param, Integer.class);
        isExist = (integer > 0) ? true : false;
        return isExist;
    }

    /**
     * 등록된 상품건수
     *
     * @return 레코드 건수
     */
    @Override
    public int countOfRecord() {
        String sql = "select count(*) from product ";
        Map<String, String> param = new LinkedHashMap<>();
        Integer rows = template.queryForObject(sql, param, Integer.class);
        return rows;
    }
}