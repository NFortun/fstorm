#pragma fstorm bolt(Float[]) returns(Float)
float sum(float * t, int s) {
	int i;
	float ret = 0;
	for (i = 0; i < s; ++i)
		ret += t[i];

	return ret;
}
