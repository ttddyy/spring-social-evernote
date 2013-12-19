#  command:
#  > thrift -out src/test/java --gen java  src/test/java/org/springframework/social/evernote/api/impl/entity/entity.thrift
#

namespace java org.springframework.social.evernote.api.impl.entity

struct Baz {
    1: optional string name;
    2: optional list<string> bazList;
    3: optional set<string> bazSet;
    4: optional map<string,string> bazMap;
}

struct Bar {
    1: optional string name;
    2: optional list<string> barList;
    3: optional set<string> barSet;
    4: optional map<string,string> barMap;
    5: optional Baz baz;
}

struct Foo {
    1: required string name;
    2: required list<string> requiredList;
    3: required set<string> requiredSet;
    4: required map<string,string> requiredMap;
    5: optional list<string> optionalList;
    6: optional set<string> optionalSet;
    7: optional map<string,string> optionalMap;
    8: optional Bar bar;
}
