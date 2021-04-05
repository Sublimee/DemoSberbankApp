package ru.sberbank.demo.app.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Client;
import ru.sberbank.demo.app.repository.ClientRepository;
import ru.sberbank.demo.app.service.AbstractPaginatedService;

import java.util.UUID;

@Service
public class ClientServiceImpl extends AbstractPaginatedService<Client, ClientNotFoundException> implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        super(ClientNotFoundException.class);
        this.clientRepository = clientRepository;
    }

    @Override
    protected PagingAndSortingRepository<Client, UUID> getDao() {
        return clientRepository;
    }

}
