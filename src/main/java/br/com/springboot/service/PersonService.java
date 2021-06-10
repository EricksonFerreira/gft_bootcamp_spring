package br.com.springboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.dto.request.PersonDTO;
import br.com.springboot.dto.response.MessageResponseDTO;
import br.com.springboot.entity.Person;
import br.com.springboot.exception.PersonNotFoundException;
import br.com.springboot.mapper.PersonMapper;
import br.com.springboot.repository.PersonRepository;

@Service
public class PersonService {
    
    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return MessageResponseDTO
                .builder()
                .message("Create person with ID "+savedPerson.getId())
                .build();
    }

    // Falta entender esse método
    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                        .map(personMapper::toDTO)
                        .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException{
        Person person = personRepository.findById(id)
                            .orElseThrow(() ->new PersonNotFoundException(id));
        // Optional<Person> optionalPerson = personRepository.findById(id);
        // if(optionalPerson.isEmpty()){
        //     throw new PersonNotFoundException(id);
        // }
        return personMapper.toDTO(person);
    }

    

}
