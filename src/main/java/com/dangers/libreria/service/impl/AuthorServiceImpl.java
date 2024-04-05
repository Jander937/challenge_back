package com.dangers.libreria.service.impl;

import com.dangers.libreria.dto.AuthorDTO;
import com.dangers.libreria.entities.AuthorEntity;
import com.dangers.libreria.entities.BookEntity;
import com.dangers.libreria.mapper.IAuthorMapper;
import com.dangers.libreria.repositories.IAuthorRepository;
import com.dangers.libreria.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    IAuthorRepository iAuthorRepository;

    @Autowired
    IAuthorMapper iAuthorMapper;

    @Override
    public AuthorDTO save(AuthorEntity dataAuthor)throws Exception{
        try {
            return this.iAuthorMapper.authorToDTO(this.iAuthorRepository.save(dataAuthor));
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }
    @Override
    public AuthorDTO seachById(Integer id) throws Exception {
        Optional<AuthorEntity> authorEntityOptional = this.iAuthorRepository.findById(id);
        if (authorEntityOptional.isPresent()){
            return this.iAuthorMapper.authorToDTO(authorEntityOptional.get());
        }else {
            throw new Exception("Author not found with the given ID " + id);
        }
    }
    @Override
    public List<AuthorDTO> seachAll()throws Exception{
        try {
            return this.iAuthorMapper.toDTOList(iAuthorRepository.findAll());
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }
    @Override
    public AuthorDTO modify(Integer id, AuthorEntity author) throws Exception{
        try {
            Optional<AuthorEntity> optionalAuthor = iAuthorRepository.findById(id);
            if (optionalAuthor.isPresent()){
                AuthorEntity existingAuthor = optionalAuthor.get();
                existingAuthor.setNameAuthor(existingAuthor.getNameAuthor());
                existingAuthor.setLastName(existingAuthor.getLastName());
                existingAuthor.setBirthdate(existingAuthor.getBirthdate());
                existingAuthor.setNationality(existingAuthor.getNationality());
                // Aquí establece los demás atributos que deseas modificar

                return iAuthorMapper.authorToDTO(iAuthorRepository.save(existingAuthor));
            } else {
                throw new Exception("Book not found with ID: " + id);
            }
        } catch (Exception error){
            throw new Exception("Failed to modify book: " + error.getMessage());
        }
    }
    @Override
    public Boolean deleteById(Integer id){
        Optional<AuthorEntity> delete = iAuthorRepository.findById(id);

        if (delete.isEmpty())
            return false;

        iAuthorRepository.deleteById(id);

        return true;
    }
}
