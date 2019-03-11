package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Equipment;

import java.util.List;
import java.util.Optional;

public class EquipmentInMemoryDao implements Dao<Equipment> {
    protected List<Equipment> equipments;

    @Override
    public List<Equipment> getAll() {
        return equipments;
    }

    @Override
    public Optional<Equipment> getById(Long id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void save(Equipment o) throws IllegalArgumentException {
        equipments.add(o);
    }

    @Override
    public void update(Equipment o) throws IllegalArgumentException {

    }

    @Override
    public void delete(Equipment o) {

    }
}