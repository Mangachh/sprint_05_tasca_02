package com.tasca02.sprint05.repositories;

import java.util.Optional;

public interface ITestGeneral<T> {
    T findByName(final String name);

    T findFirstByOrderByIdDesc();
}
