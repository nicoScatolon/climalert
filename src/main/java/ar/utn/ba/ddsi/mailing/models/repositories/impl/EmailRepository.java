package ar.utn.ba.ddsi.mailing.models.repositories.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IEmailRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EmailRepository implements IEmailRepository {
    private final Map<Long, Email> emails = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Email save(Email email) {
        if (email.getId() == null) {
            return this.create(email);
        } else {
            return this.update(email);
        }
    }

    @Override
    public List<Email> findAll() {
        return new ArrayList<>(emails.values());
    }

    @Override
    public List<Email> findByEnviado(boolean enviado) {
        return emails.values().stream()
                .filter(email -> email.isEnviado() == enviado)
                .toList();
    }

    @Override
    public Optional<Email> findById(Long id) {
        return Optional.ofNullable(emails.get(id));
    }

    @Override
    public void delete(Email email) {
        if (email.getId() != null) {
            emails.remove(email.getId());
        }
    }

    private Email create(Email email) {
        Long id = idGenerator.getAndIncrement();
        email.setId(id);
        emails.put(id, email);
        return email;
    }

    private Email update(Email email) {
        emails.put(email.getId(), email);
        return email;
    }
} 