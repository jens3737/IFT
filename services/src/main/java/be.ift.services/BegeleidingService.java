package be.ift.services;

import be.ift.domain.Begeleiding;

import java.util.List;

/**
 * Created by VXFBD43 on 20/03/2017.
 */
public interface BegeleidingService {
    Begeleiding saveBegeleiding(Begeleiding begeleiding);

    List<Begeleiding> BegeleidingFBegeleider(int id);
}
