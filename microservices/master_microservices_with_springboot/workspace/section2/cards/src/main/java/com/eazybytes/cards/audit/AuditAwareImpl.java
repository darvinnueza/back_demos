package com.eazybytes.cards.audit;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.AuditorAware;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }
}
