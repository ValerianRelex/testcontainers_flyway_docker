package ru.swvalerian.testcontainersdemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.swvalerian.testcontainersdemo.entity.Temp;

@Repository
public interface TempRepository extends JpaRepository<Temp, String> {
}
