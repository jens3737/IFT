package be.ift.services.servicesimpl;

import be.ift.domain.Rol;
import be.ift.repositories.RolRepository;
import be.ift.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final static int PAGESIZE = 10;

    @Autowired
    private RolRepository rolRepositotry;

    @Override
    public Rol getOneRol(Integer id) {
        return rolRepositotry.findOne(id);
    }

    @Override
    public List<Rol> getAllRollen(int pageNumber) {
        return null;
    }

    @Override
    public double getAantalPaginas() {
        return 0;
    }

    @Override
    public List<Rol> getAllRollen() {
        List<Rol> rollen = new ArrayList<>();
        rolRepositotry.findAll()
                .forEach(rollen::add);
        return rollen;
    }

    @Override
    public Rol saveRol(Rol rol) {
        return null;
    }
}
