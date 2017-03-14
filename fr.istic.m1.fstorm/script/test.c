#pragma fstorm spout returns(Integer)
int lady_oscar() {
	return 0;
}

void do_it() {
}

#pragma fstorm unused
const int fucks_given = 0;

#include <stdio.h>

#pragma fstorm bolt(Integer)
void oscar_francois_de_jarjayes(int i) {
	printf("%d\n", i);
}

#pragma fstorm bolt(Integer) returns(Integer)
int id(int i) {
	return i;
}

#pragma fstorm bolt(java.util.List<Integer>, String) returns(Integer[])
int* marie_antoinette(int* a, const char* c) {
	(void) c;
	return a;
}

#pragma fstorm spout returns(java.util.Map<String, java.lang.Integer>)
void* andre() {
	return 0;
}
