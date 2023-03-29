package it.softx.northwind.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.softx.northwind.model.dto.ProductResourceDto;
import it.softx.northwind.model.service.ProductMapperService;
import it.softx.northwind.model.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductsController {

	@Autowired
	private ProductService prodService;
	@Autowired
	private ProductMapperService prodMap;

	@GetMapping
	public List<ProductResourceDto> getAll() {
		return prodMap.mapToResourceList(prodService.readAll());
	}

	//DAL FRONTEND VOGLIO: (-LA CATEGORIA) e (-0 se senza filtro per prezzo, 1 se prezzo crescente, 2 se prezzo decrescente)
	@GetMapping("/{p}")
	public ResponseEntity<Object> getByCategoryAndPriceAscOrDesc(@RequestParam(name = "c") String c,
			@RequestParam(name = "p") int p) {
		switch (p) {
		case 0:
			return ResponseEntity.ok(prodMap.mapToResourceList(prodService.readByCategory(c)));
		case 1:
			return ResponseEntity.ok(prodMap.mapToResourceList(prodService.readByCategoryAsc(c)));
		case 2:
			return ResponseEntity.ok(prodMap.mapToResourceList(prodService.readByCategoryDesc(c)));
		default: return ResponseEntity.badRequest().build();
		}
	}

}
