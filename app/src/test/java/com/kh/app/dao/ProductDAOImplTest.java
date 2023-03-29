package com.kh.app.dao;

import com.kh.app.domain.entity.Product;
import com.kh.app.domain.product.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


//메소드 별, 건바이건으로 테스트 해볼수 있다.(비동기적)
@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

    @Autowired //참조하기 위해서 오토와이얼드 사용한다.
    ProductDAO productDAO; //스프링 컨테이너에서 참조할 수 있는,

    //등록
    @Test //테스트할 어노테이션을 붙여준다.
    @DisplayName("상품등록")
    //테스트 case를 정의
    void save(){
        Product product = new Product();
        product.setPname("복사기");
        product.setQuantity(10L);
        product.setPrice(1000000L);

        //productDAO.save(product); 반환값
        Long productId = productDAO.save(product);
        log.info("productId={}",productId);
        //Assertions -> assertj 
        //정적 메소드, (실제값), (예상값) 
        //0보다 크면 실행이다.
        //프로그램화로 체크를 찍는다.
        Assertions.assertThat(productId).isGreaterThan(0L);
    }

    //조회
    @Test
    @DisplayName("상품조회")
    void findById(){
        Long productId = 4L;
        Optional<Product> product = productDAO.findById(productId);
//        if(product.isPresent()) {
//            log.info("product={}", product.get());
//        }else{
//            log.info("조회한 결과 없음");
//        }
//        Assertions.assertThat(product.stream().count())
//                .isEqualTo(1);

        //예외 발생 시 빨갛게 표시
        Product findedProduct = product.orElseThrow();  // 없으면, java.NoSuchElementException
        //상품들의 값들을 들고와서 예상되는 값이 맞는지 확인
        Assertions.assertThat(findedProduct.getPname()).isEqualTo("복사기");
        Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(10L);
        Assertions.assertThat(findedProduct.getPrice()).isEqualTo(1000000L);
    }

    //수정
    @Test
    @DisplayName("상품수정")
    void update(){
        Long productId = 4L;

        Product product = new Product();
        product.setPname("복사기_수정");
        product.setQuantity(10L);
        product.setPrice(1000000L);

        //update(productId, product) 매개값을 던진다.
        int updatedRowCont = productDAO.update(productId, product);

        //수정결과 확인
        Optional<Product> findedProduct = productDAO.findById(productId);

        //예상값과 실제값이 맞는지 확인
        Assertions.assertThat(updatedRowCont).isEqualTo(1);
        Assertions.assertThat(findedProduct.get().getPname()).isEqualTo(product.getPname());
        Assertions.assertThat(findedProduct.get().getQuantity()).isEqualTo(product.getQuantity());
        Assertions.assertThat(findedProduct.get().getPrice()).isEqualTo(product.getPrice());
    }

    //삭제
    @Test
    @DisplayName("상품삭제")
    void delete() {
        Long productId = 4L;
        int deleteRowCount = productDAO.delete(productId);
        Optional<Product> findedProduct = productDAO.findById(productId);
//        Product product = findedProduct.orElseThrow();

        //case1)
        Assertions.assertThat(findedProduct.ofNullable("없음")
                .orElseThrow())
                .isEqualTo("없음");

        //case2
        //예상되는 입셉션이 일어나면 참이다.
        Assertions.assertThatThrownBy(() -> findedProduct.orElseThrow())
                .isInstanceOf(NoSuchElementException.class);

    }

    //목록
    @Test
    @DisplayName("상품목록")
    void findAll(){
        List<Product> list = productDAO.findAll();

//        //case1) 콘솔에 찍어보기
//        for(Product product : list){
//            log.info("product={}", product);
//        }
//
//        //case2) 향상된 포문
        //stream()으로 변환 -> ramda식으로 표현할 수 있다.
        //요소 하나하나를 꺼내서 확인
//        list.stream().forEach(product -> log.info("product={}", product));

        //0보다 크면 참이다.
        Assertions.assertThat(list.size()).isGreaterThan(0);
    }
    
    @Test
    @DisplayName("상품존재")
    void isExist(){
        Long productId = 0L;
        boolean exist = productDAO.isExist(productId);
        Assertions.assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("상품존재무")
    void isExist2(){
        Long productId = 0L;
        boolean exist = productDAO.isExist(productId);
        Assertions.assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("전체삭제")
    void deleteAll(){
        int deletedRows = productDAO.deleteAll();
        int countOfRecord = productDAO.countOfRecord();
        Assertions.assertThat(countOfRecord).isEqualTo(0);
        log.info("deletedRows={}", deletedRows);
    }

    @Test
    @DisplayName("레코드 건수")
    void countOfRecord(){
        int countOfRecord = productDAO.countOfRecord();
        log.info("countOfRecord={}", countOfRecord);
    }
}