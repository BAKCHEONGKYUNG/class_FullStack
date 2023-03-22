package com.kh.myProduct.web;

import com.kh.myProduct.dao.Product;
import com.kh.myProduct.svc.ProductSVC;
import com.kh.myProduct.web.form.DetailForm;
import com.kh.myProduct.web.form.SaveForm;
import com.kh.myProduct.web.form.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

//view에 data를 담아서 전달하는 방식을 사용 중,
@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductSVC productSVC;

    //등록양식
    @GetMapping("/add")
    public String saveForm(Model model){
        SaveForm saveForm = new SaveForm();
        model.addAttribute("form", saveForm);
        return "product/saveForm";  //view를 찾는 상대 경로
    }

    //등록처리
    @PostMapping("/add")
    public String save(
//            @Param("pname") String pname,
//            @Param("quantity") String quantity,
//            @Param("price") Long price
            //web.SaveFrom

            //데이터 검증1)
            @Valid @ModelAttribute("form") SaveForm saveForm,
            BindingResult bindingResult, //검증결과를 담는 객체
            RedirectAttributes redirectAttributes
            ) {
//        log.info("pname={}, quantity={}, price={}", pname,quantity,price);
        log.info("saveForm={}", saveForm);

        //어노테이션 기반 검증
        if(bindingResult.hasErrors()){
            log.info("bindingResult={}", bindingResult);
            return "product/saveForm"; //값이 없으면 등록하는 화면을 나타낸다.
        }

        //필드 오류
        if(saveForm.getQuantity() == 100){
            bindingResult.rejectValue("quantity", "", "수량 100 입력불가!");
        }

        //글로벌 오류
        //비즈니스 룰) 총액(상품수량*단가) 1000만원 초과 금지
        if(saveForm.getQuantity() * saveForm.getPrice() > 10_000_000L){
            bindingResult.reject("",null,"총액(상품수량*단가) 1000만원 초과 금지");
        }

        //등록된 갯수 만큼
        if(saveForm.getQuantity() > 1 && saveForm.getQuantity() < 10){
            bindingResult.reject("",null,"10이상 이어야한다.");
        }

        //데이터 검증2)
        if(bindingResult.hasErrors()){
            log.info("bindingResult={}", bindingResult);
            return "product/saveForm"; //값이 없으면 등록하는 화면을 나타낸다.
        }



        //등록
        Product product = new Product();
        //데이터 베이스 저장
        product.setPname(saveForm.getPname());
        product.setQuantity(saveForm.getQuantity());
        product.setPrice(saveForm.getPrice());


//        Long save = productSVC.save(product);
        Long savedProductId = productSVC.save(product);
        //redirectAttributes 받아서
        redirectAttributes.addAttribute("id", savedProductId);
        //조회하면서 가야하기 때문에
        return "redirect:/products/{id}/detail";
    }

    //등록한 상품조회
    @GetMapping("/{id}/detail")
    public String findById(
            @PathVariable("id") Long id,
            Model model
            ){
        //값이 없을 수 있기 때문에 Optional로 값을 감싸준다.
        Optional<Product> findedProduct = productSVC.findById(id);
        Product product = findedProduct.orElseThrow();

        DetailForm detailForm = new DetailForm();
        detailForm.setProductId(product.getProductId());
        detailForm.setPname(product.getPname());
        detailForm.setQuantity(product.getQuantity());
        detailForm.setPrice(product.getPrice());

        model.addAttribute("form", product);
        return "product/detailForm";
    }

    //수정양식
    @GetMapping("/{id}/edit")
    public String updateForm(
            @PathVariable("id") Long id,
            Model model
    ){
        Optional<Product> findedProduct = productSVC.findById(id);
        Product product = findedProduct.orElseThrow();

        UpdateForm updateForm = new UpdateForm();
        updateForm.setProductId(product.getProductId());
        updateForm.setPname(product.getPname());
        updateForm.setQuantity(product.getQuantity());
        updateForm.setPrice(product.getPrice());

        //view에서 참고할 수 있게
        model.addAttribute("form", updateForm);
        return "product/updateForm";
    }

    //수정(처리)
    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable("id") Long productId,
            @ModelAttribute("form") UpdateForm updateForm,
            //id값 전달을 위해
            RedirectAttributes redirectAttributes
    ){

        Product product = new Product();
        product.setProductId(productId);
        product.setPname(updateForm.getPname());
        product.setQuantity(updateForm.getQuantity());
        product.setPrice(updateForm.getPrice());

        productSVC.update(productId, product);

        redirectAttributes.addAttribute("id", productId);
        return "redirect:/products/{id}/detail";
    }
    //삭제(조회화면에서)
    @GetMapping("/{id}/del")
    public String deleteById(@PathVariable("id") Long productId){

        productSVC.delete(productId);

        return "redirect:/products";
    }

    //목록
    @GetMapping
    public String findAll(Model model){

        List<Product> products = productSVC.findAll();
        model.addAttribute("products", products);

        return "product/all";
    }

}
