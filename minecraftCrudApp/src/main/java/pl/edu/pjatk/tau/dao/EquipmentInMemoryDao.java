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
        if (equipments.stream().noneMatch(equipment -> equipment.getId().equals(id)))
            throw new IllegalArgumentException("equipment with id " + id + " not exists");
        return equipments.stream().filter(equipment -> equipment.getId().equals(id)).findFirst();
    }

    @Override
    public void save(Equipment o) throws IllegalArgumentException {
        if (equipments.stream().anyMatch(equipment -> equipment.getId().equals(o.getId())))
            throw new IllegalArgumentException("equipment already exists");
        equipments.add(o);
    }

    @Override
    public void update(Equipment o) throws IllegalArgumentException {
        if (equipments.stream().noneMatch(equipment -> equipment.getId().equals(o.getId())))
            throw new IllegalArgumentException("equipment not exists");
        equipments.add(o.getId().intValue() - 1, o);
    }

    @Override
    public void delete(Equipment o) {
        int id = o.getId().intValue();
        equipments.remove(id);
    }
}