package gift.controller;

import gift.model.Product;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductViewController {
  private final ProductService productService;

  public ProductViewController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public String listProducts(Model model) {
    model.addAttribute("products", productService.findAll());
    return "product-list";
  }

  @GetMapping("/new")
  public String showCreateForm(Model model) {
    model.addAttribute("product", new Product());
    return "product-form";
  }

  @PostMapping
  public String createProduct(@ModelAttribute Product product) {
    productService.save(product);
    return "redirect:/products";
  }

  @GetMapping("/edit/{id}")
  public String showEditForm(@PathVariable Long id, Model model) {
    model.addAttribute("product", productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id)));
    return "product-form";
  }

  @PostMapping("/{id}")
  public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
    product.setId(id);
    productService.save(product);
    return "redirect:/products";
  }

  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteById(id);
    return "redirect:/products";
  }
}
