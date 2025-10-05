package com.food_delivery_app.restaurant_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/search")
@RequiredArgsConstructor
public class RestaurantSearchController {
    //TO BE IMPLEMENTED LATER
//    Endpoints
//    Method	Endpoint	Description
//    GET	/restaurants/search?name={name}	Search restaurant by name
//    GET	/restaurants/search?location={city}	Search restaurant by city
//    GET	/restaurants/search?category={category}	Filter by cuisine type
//    GET	/restaurants/search?open=true	Filter only open restaurants
//
//👉 We can later enhance this with:
//
//    Pagination (?page=1&size=10)
//
//    Sorting (?sortBy=rating&order=desc)
//
//    ElasticSearch integration (for fuzzy search)


//    CategoryController (Optional but realistic)
//
//    To manage the categories that restaurants or menu items belong to.
//
//            Endpoints
//    Method	Endpoint	Description
//    POST	/categories	Add new category
//    GET	/categories	List all categories
//    PUT	/categories/{id}	Update category
//    DELETE	/categories/{id}	Delete category
}
