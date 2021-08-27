package com.efimov.messageStore.service.impl.specifications;

import com.efimov.messageStore.entity.Message;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class MessageSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public MessageSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public MessageSpecificationsBuilder with(String key, String operation, Object value,Boolean orPredicate) {
        params.add(new SearchCriteria(key , operation, value,orPredicate));
        return this;
    }

    public Specification<Message> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(MessageSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new MessageSpecification(params.get(i)))
                    : Specification.where(result).and(new MessageSpecification(params.get(i)));
        }
        return result;
    }
}
