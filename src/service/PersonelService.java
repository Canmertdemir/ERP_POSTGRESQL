package service;

import dao.PersonelDAO;
import model.Personel;

import java.util.List;

public class PersonelService {
    private final PersonelDAO dao = new PersonelDAO();

    public List<Personel> listele() {
        return dao.getAll();
    }
}
