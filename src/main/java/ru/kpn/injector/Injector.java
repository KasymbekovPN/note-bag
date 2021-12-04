package ru.kpn.injector;

import ru.kpn.objectFactory.datum.Datum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.rawMessage.RawMessage;

// TODO: 04.12.2021 factory::create must take datum & injector
public interface Injector<RT, D extends Datum<? extends DatumType>> {
     void inject(ObjectFactory<D, RT, RawMessage<String>> factory);
}
