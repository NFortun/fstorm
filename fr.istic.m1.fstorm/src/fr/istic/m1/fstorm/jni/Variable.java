package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Variable {
    private String _name;
    private JType _jtype;
    private CType _ctype;
    
    public Variable(JType jtype, String value) {
    	this(jtype, value, false);
    }

    public Variable(CType ctype, String value) {
    	this(ctype, value, false);
    }
    
    public Variable(JType jtype, String value, boolean isParam) {
        _name = WrapperEnvironment.getScope().fresh();
        _jtype = jtype;
        _ctype = null;

        if (!isParam)
        	WrapperEnvironment.getBuffer().append(_jtype.getJniType()+" "+_name+((value != null) ? " = "+value+";\n" : ";\n"));
    }

    public Variable(CType ctype, String value, boolean isParam) {
        _name = WrapperEnvironment.getScope().fresh();
        _jtype = null;
        _ctype = ctype;

        if (!isParam)
        	WrapperEnvironment.getBuffer().append(_ctype.getJniType()+" "+_name+((value != null) ? " = "+value+";\n" : ";\n"));
    }
    
    public List<Variable> toKernel(CType c) {
        if (_ctype == null) {
            return _jtype.toKernel(this, c);
        } else {
        	List<Variable> ret = new ArrayList<>();
        	ret.add(new Variable(c, getName()));
            return ret;
        }
    }
    
    public Variable toJava(JType j) {
    	if (_jtype == null) {
    		return _ctype.toJava(Arrays.asList(this), j);
    	} else {
    		return new Variable(j, getName());
    	}
    }
    
    public String getName() {
    	return _name;
    }

	public JNIType getType() {
		if (_ctype == null)
			return _jtype;
		else
			return _ctype;
	}

	public JType getJType() {
		return _jtype;
	}

	public CType getCType() {
		return _ctype;
	}
}