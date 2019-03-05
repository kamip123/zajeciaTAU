package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Equipment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EquipmentInMemoryDao implements Dao<Equipment> {
    protected Map<Long,Equipment> equipments;

    @Override
    public Optional<Equipment> get(Long id) {
        return Optional.ofNullable(equipments.get(id));
    }

    @Override
    public List<Equipment> getAll() {
        return null;
    }

    @Override
    public void save(Equipment e) {

    }

    @Override
    public void update(Equipment e) throws IllegalArgumentException {
        if (!equipments.containsKey(e.getId()))
            throw new IllegalArgumentException("Key does not exist");
        equipments.put(e.getId(), e);
    }

    @Override
    public void delete(Equipment e) {
        
    }
}