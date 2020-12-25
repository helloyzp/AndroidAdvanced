// IMyAidl.aidl
package com.binder.service;

// Declare any non-default types here with import statements

import com.binder.service.Person;//Person即使在同一个包下，也要声明，只要是非默认的类型，都要声明。

interface IMyAidl {
    void addPerson(in Person person);

    List<Person> getPersonList();
}
