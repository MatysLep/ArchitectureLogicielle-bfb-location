package imt.archi.bfb.interfaces.rest.common.model.input;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.function.Function;

import static lombok.EqualsAndHashCode.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatableProperty<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static <T extends Serializable> UpdatableProperty<T> empty() {
        return new UpdatableProperty<>(false, null);
    }

    public static <T extends Serializable> UpdatableProperty<T> makesChanges(final T newValue){
        return new UpdatableProperty<>(true, newValue);
    }

    @Getter(AccessLevel.PROTECTED)
    @Exclude
    private final boolean updated;
    private final T value;

    public T defaultIfNotOverwrite(final T defaultValue){
        return this.isUpdated()
                ? this.getValue()
                : defaultValue;
    }

    public <R> R defaultIfNotOverwrite(final Function<T, R> transform, final R defaultValue) {
        return this.isUpdated()
                ? transform.apply(this.getValue())
                : defaultValue;
    } // TODO voir pk different du prof

    @Override
    public String toString(){
        return this.isUpdated()
                ? String.format("UpdatableProperty[%s]", this.getValue())
                : "UpdatableProperty.empty";
    }
}