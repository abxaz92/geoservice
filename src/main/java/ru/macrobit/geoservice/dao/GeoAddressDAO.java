package ru.macrobit.geoservice.dao;

import org.springframework.data.mongodb.core.MongoOperations;
import ru.macrobit.geoservice.common.AbstractBaseService;
import ru.macrobit.geoservice.common.SecondMongotemplateProducer;
import ru.macrobit.geoservice.search.pojo.GeoAddress;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by david on 12.02.17.
 */
@Singleton
@Startup
public class GeoAddressDAO extends AbstractBaseService<GeoAddress> {

    @EJB
    private SecondMongotemplateProducer mongotemplateProducer;

    public GeoAddressDAO() {
        super(GeoAddress.class);
    }

    private Map<String, GeoAddress> cache = new HashMap<>();

    @PostConstruct
    public void init() {
        this.cache = find(null).stream().collect(Collectors.toMap(GeoAddress::getId, Function.identity()));
    }

    public Map<String, GeoAddress> getCache() {
        return this.cache;
    }


    @Override
    protected MongoOperations getMt() {
        return mongotemplateProducer.getMt();
    }
}