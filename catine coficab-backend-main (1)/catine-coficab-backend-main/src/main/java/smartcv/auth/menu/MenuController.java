package smartcv.auth.menu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcv.auth.serviceImpl.MenuService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // Method to get all menus
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllMenus() {
        List<Menu> menus = menuService.getAllMenus();

        List<Map<String, Object>> response = menus.stream().map(menu -> {
            Map<String, Object> menuData = new HashMap<>();
            menuData.put("id", menu.getId());
            menuData.put("date", menu.getMenuDate());
            menuData.put("entree", menu.getEntree());
            menuData.put("platPrincipal", menu.getMainCourse());
            menuData.put("garniture", menu.getGarnish());
            menuData.put("dessert", menu.getDessert());
            menuData.put("sandwiches", menu.getSandwiches());

            return menuData;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMenu(@RequestBody Menu menu) {
        // Check if the menuDate is provided
        if (menu.getMenuDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Menu date is required.");
        }

        // Create a new Menu object, only setting the fields that are provided
        Menu newMenu = new Menu();
        newMenu.setMenuDate(menu.getMenuDate());

        // Only set optional fields if they are provided
        if (menu.getEntree() != null && !menu.getEntree().isEmpty()) {
            newMenu.setEntree(menu.getEntree());
        }
        if (menu.getMainCourse() != null && !menu.getMainCourse().isEmpty()) {
            newMenu.setMainCourse(menu.getMainCourse());
        }
        if (menu.getGarnish() != null && !menu.getGarnish().isEmpty()) {
            newMenu.setGarnish(menu.getGarnish());
        }
        if (menu.getDessert() != null && !menu.getDessert().isEmpty()) {
            newMenu.setDessert(menu.getDessert());
        }
        if (menu.getSandwiches() != null && !menu.getSandwiches().isEmpty()) {
            newMenu.setSandwiches(menu.getSandwiches());
        }

        // Check for existing menus for the same date

        if (menu.getMenuDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Menu date is required.");
        }

        // Save the new menu to the database
        Menu addedMenu = menuService.addMenu(newMenu);
        return ResponseEntity.ok(addedMenu);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable int id, @RequestBody Menu menuDetails) {
        Menu updatedMenu = menuService.updateMenu(id, menuDetails);
        System.out.println("Updated menu: " + updatedMenu); // Log the updated menu
        return ResponseEntity.ok(updatedMenu);
    }




    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable int id) {
        boolean isDeleted = menuService.deleteMenu(id);

        if (isDeleted) {
            return ResponseEntity.ok("Menu deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu not found");
        }
    }
}
