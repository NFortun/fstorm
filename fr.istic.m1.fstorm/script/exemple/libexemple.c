#pragma fstorm bolt(Float[]) returns(Float)
float sum(float * t, int s) {
	int i;
	float ret = 0;
	for (i = 0; i < s; ++i)
		ret += t[i];

	return ret;
}

JNIEXPORT jobject JNICALL Java_fr_fstorm_exemple_SumBolt_sum(JNIEnv* b, jclass f, jobjectArray g) {
jclass a = (*b)->FindClass(b, "java/lang/Float");
jmethodID c = (*b)->GetMethodID(b, a, "<init>", "()V");
jmethodID d = (*b)->GetMethodID(b, a, "floatValue", "()F");
jmethodID e = (*b)->GetStaticMethodID(b, a, "valueOf", "(F)Ljava/lang/Float");
int h = (*b)->GetArrayLength(b, g);
float* i = malloc(h*sizeof(float));
int j;
for (j=0; j<h; ++j) {
float k;
k = (*b)->CallFloatMethod(b, (*b)->GetObjectArrayElement(b, g, j), d);
i[j] = k;
}
float l = sum(i, h);
jobject m = (*b)->CallStaticObjectMethod(b, a, e, l);
return m;

}
