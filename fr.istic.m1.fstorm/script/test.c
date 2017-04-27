#pragma fstorm spout returns(Integer)
int lady_oscar() {
	return 0;
}

void do_it() {
}

//#pragma fstorm unused
const int fucks_given = 0;

//#include <stdio.h>

#pragma fstorm bolt(Integer)
void oscar_francois_de_jarjayes(int i) {
//	printf("%d\n", i);
}

#pragma fstorm bolt(Integer) returns(Integer)
int id(int i) {
	return i;
}

typedef struct point {
	int x;
	int y;
} point;

#pragma fstorm bolt(point[], String) returns(point)
int marie_antoinette(point* a, int n, char* c) {
	if (n > 0)
		return a[0];
	else {
		point ret;
		ret.x = 0;
		ret.y = 0;

		return ret;
	}
}
/*
#pragma fstorm spout returns(java.util.Map<String, java.lang.Integer>)
void* andre() {
	return 0;
}
*/
