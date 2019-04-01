package pl.edu.pjatk.tau.minecraft.dao;

import pl.edu.pjatk.tau.minecraft.domain.Equipment;

import java.util.List;
import java.util.Optional;

public interface MinecraftDao {
    List<Equipment> getAll();
    Optional<Equipment> getById(Long id);
    void save(Equipment o);
    void update(Equipment o);
    void delete(Equipment o);
}
