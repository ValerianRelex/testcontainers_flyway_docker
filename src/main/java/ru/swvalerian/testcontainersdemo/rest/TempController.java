package ru.swvalerian.testcontainersdemo.rest;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.swvalerian.testcontainersdemo.entity.Temp;
import ru.swvalerian.testcontainersdemo.repo.TempRepository;

@RestController
@RequiredArgsConstructor
public class TempController {
    private final TempRepository tempRepository;

    @GetMapping("/{city}")
    public Temp getTempFrom(@PathVariable(value = "city") String city) {
	Temp temp = new Temp();
	temp.setId(UUID.randomUUID().toString());
	temp.setCity(city);
	temp.setTempInCelsius(-25.0); // тестовые данные
	temp.setTempInF(temp.getTempInCelsius() * 1.8 + 32); // тестовые данные
	return tempRepository.save(temp);
    }
}
