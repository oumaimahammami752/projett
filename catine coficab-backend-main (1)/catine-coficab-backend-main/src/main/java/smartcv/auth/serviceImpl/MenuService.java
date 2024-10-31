package smartcv.auth.serviceImpl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartcv.auth.menu.Menu;
import smartcv.auth.menu.MenuRepository;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MenuService {

    @Autowired
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // Method to get all menus
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    // Method to add a new menu
    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    // Method to get menus by date
    public List<Menu> getMenusByDate(Date date) {
        return menuRepository.findByMenuDate(date);
    }

    // Method to update an existing menu
    public Menu updateMenu(int id, Menu menuDetails) {
        return menuRepository.findById(id).map(menu -> {
            // Only update fields if they are present in menuDetails
            if (menuDetails.getEntree() != null) menu.setEntree(menuDetails.getEntree());
            if (menuDetails.getMainCourse() != null) menu.setMainCourse(menuDetails.getMainCourse());
            if (menuDetails.getGarnish() != null) menu.setGarnish(menuDetails.getGarnish());
            if (menuDetails.getDessert() != null) menu.setDessert(menuDetails.getDessert());
            if (menuDetails.getSandwiches() != null) menu.setSandwiches(menuDetails.getSandwiches());

            return menuRepository.save(menu);
        }).orElseThrow(() -> new NoSuchElementException("Menu not found with id " + id));
    }


    // Method to delete a menu
    @Transactional
    public boolean deleteMenu(int menuId) {
        if (menuRepository.existsById(menuId)) {
            menuRepository.deleteById(menuId);
            return true;
        } else {
            return false;
        }
    }
}
