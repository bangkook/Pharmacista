package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.dto.FavoriteItemDTO;
import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.service.FavoriteListService;
import com.example.pharmacysystem.service.ProductService;
import com.example.pharmacysystem.utils.FavoriteItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/favorites")
@RestController
@CrossOrigin()
public class FavoriteListController {

    @Autowired
    private FavoriteListService favoriteListService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FavoriteItemMapper favoriteItemMapper;

    @PostMapping("/add")
    public ResponseEntity<Boolean> save(@RequestBody FavoriteItem favoriteItem) {
        boolean isSuccess = favoriteListService.add(favoriteItem);
        if (isSuccess) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody FavoriteItem favoriteItem) {
        boolean isSuccess = favoriteListService.delete(favoriteItem);
        if (isSuccess) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/get/{userId}")
    @ResponseBody
    public List<FavoriteItemDTO> findByUserId(@PathVariable("userId") int userId) {
        List<FavoriteItem> favoriteItems = favoriteListService.findByUserId(userId);

        return favoriteItems.stream()
                .map(favoriteItem ->
                        favoriteItemMapper.toDTO(favoriteItem,
                                productService.getProductBySerialNumber(favoriteItem.getProductSN())))
                .collect(Collectors.toList());
    }

    @GetMapping("/get-sorted/{userId}")
    @ResponseBody
    public List<FavoriteItemDTO> findByUserIdSortedAsc(@PathVariable("userId") int userId) {
        List<FavoriteItem> favoriteItems = favoriteListService.findByUserIdSorted(userId);

        return favoriteItems.stream()
                .map(favoriteItem ->
                        favoriteItemMapper.toDTO(favoriteItem,
                                productService.getProductBySerialNumber(favoriteItem.getProductSN())))
                .collect(Collectors.toList());

    }

}
