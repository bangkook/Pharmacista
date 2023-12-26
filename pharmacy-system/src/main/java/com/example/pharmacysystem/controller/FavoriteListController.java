package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.service.FavoriteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO : Settle on whether to implement one or two-way sorting
@RequestMapping("/favorites")
@RestController
@CrossOrigin()
public class FavoriteListController {

    @Autowired
    private FavoriteListService favoriteListService;

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
    public List<FavoriteItem> findByUserId(@PathVariable("userId") int userId) {
        return favoriteListService.findByUserId(userId);
    }

    @GetMapping("/get-sorted/{userId}")
    @ResponseBody
    public List<FavoriteItem> findByUserIdSortedAsc(@PathVariable("userId") int userId) {
        return favoriteListService.findByUserIdSorted(userId, true);
    }

}
