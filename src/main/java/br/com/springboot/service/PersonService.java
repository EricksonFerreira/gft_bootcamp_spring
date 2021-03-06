package br.com.springboot.service;

import java.util.List;
// import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.dto.request.PersonDTO;
import br.com.springboot.dto.response.MessageResponseDTO;
import br.com.springboot.entity.Person;
import br.com.springboot.exception.PersonNotFoundException;
import br.com.springboot.mapper.PersonMapper;
import br.com.springboot.repository.PersonRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
    
    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return createdMethodResponse(savedPerson.getId(),"Create person with ID ");

    }

    // Falta entender esse método
    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                        .map(personMapper::toDTO)
                        .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException{
        Person person = verifyIfExists(id);
        // Optional<Person> optionalPerson = personRepository.findById(id);
        // if(optionalPerson.isEmpty()){
        //     throw new PersonNotFoundException(id);
        // }
        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, @Valid PersonDTO personDTO) throws PersonNotFoundException {

        verifyIfExists(id);
        
        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createdMethodResponse(updatedPerson.getId(),"Updated person with ID ");
    }
    
    private Person verifyIfExists(Long id) throws PersonNotFoundException{
        return personRepository.findById(id)
                                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createdMethodResponse(Long id, String message){
        return MessageResponseDTO
                .builder()
                .message(message+id)
                .build();
    }
}
