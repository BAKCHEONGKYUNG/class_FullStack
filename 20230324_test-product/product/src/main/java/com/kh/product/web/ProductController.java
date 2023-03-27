package com.kh.product.web;

import com.kh.product.dao.Product;
import com.kh.product.svc.ProductSVC;
import com.kh.product.web.form.DetailForm;
import com.kh.product.web.form.SaveForm;
import com.kh.product.web.form.UpdateForm;
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
        model.addAttribute("saveForm", saveForm);
        return "product/saveForm"; //view 를 찾는 상대 경로
    }

    //등록처리
    @PostMapping("/add")
    public String save(
            @Valid @ModelAttribute SaveForm saveForm,
            RedirectAttributes redirectAttributes
    ){
        Product product = new Product();
        //db 저장
        product.setPname(saveForm.getPname());
        product.setQuantity(saveForm.getQuantity());
        product.setPrice(saveForm.getPrice());

        Long savePId = productSVC.save(product);

        redirectAttributes.addAttribute("id", savePId);

        return "redirect:/products/{id}/detail";
    }

    //등록한 상품조회
    @GetMapping("/{id}/detail")
    public String findById(
            @PathVariable("id") Long pId,
            Model model
    ){
        Optional<Product> findedProduct = productSVC.findById(pId);

        Product product = findedProduct.orElseThrow();

        DetailForm detailForm = new DetailForm();
        detailForm.setPId(product.getPId());
        detailForm.setPname(product.getPname());
        detailForm.setQuantity(product.getQuantity());
        detailForm.setPrice(product.getPrice());

        model.addAttribute("detailForm", detailForm);

        return "product/detailForm";
    }

    //수정양식
    @GetMapping("/{id}/edit")
    public String updateForm(
            @PathVariable("id") Long pId,
            Model model
    ){
        Optional<Product> findedProduct = productSVC.findById(pId);

        Product product = findedProduct.orElseThrow();

        UpdateForm updateForm = new UpdateForm();

        updateForm.setPId(product.getPId());
        updateForm.setPname(product.getPname());
        updateForm.setQuantity(product.getQuantity());
        updateForm.setPrice(product.getPrice());

        model.addAttribute("updateForm", updateForm);

        return "product/updateForm";
    }

    //수정처리
    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable("id") Long pId,
            @Valid @ModelAttribute UpdateForm updateForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){

        if (bindingResult.hasErrors()){
            log.info("bindingResult={}", bindingResult);
            return "product/updateForm";
        }

        //정산처리
        Product product = new Product();
        product.setPId(pId);
        product.setPname(updateForm.getPname());
        product.setQuantity(updateForm.getQuantity());
        product.setPrice(updateForm.getPrice());

        productSVC.update(pId, product);

        redirectAttributes.addAttribute("id", pId);

        return "redirect:/products/{id}/detail";
    }

    //삭제(조회화면에서)
    @GetMapping("/{id}/del")
    public String deleteById(
            @PathVariable("id") Long pId
    ){
        productSVC.delete(pId);

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
