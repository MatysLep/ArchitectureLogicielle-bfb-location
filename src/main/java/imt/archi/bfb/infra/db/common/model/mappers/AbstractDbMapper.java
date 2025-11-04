package imt.archi.bfb.infra.db.common.model.mappers;

public abstract class AbstractDbMapper<To, From> {
    public abstract To from(final From from);
    public abstract From to(final To to);
}
