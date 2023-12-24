package core.AST.Type;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public class FunctionType {
    ArrayList<ValueType> parameters;
    ArrayList<ValueType> results;

    FunctionType(ArrayList<ValueType> parameters, ArrayList<ValueType> results) {
        this.parameters = parameters;
        this.results = results;
    }

    static public Result<FunctionType, ParseException> parse(Parser parser) {
        if (parser.nextByte((byte) 0x60) instanceof Err(ParseException e)) {
            return new Err<>(e);
        }
        ArrayList<ValueType> parameters;
        ArrayList<ValueType> results;
        switch (parser.nextVector(ValueType::parse)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ArrayList<ValueType> params) -> parameters = params;
        }
        switch (parser.nextVector(ValueType::parse)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ArrayList<ValueType> rsts) -> results = rsts;
        }
        return new Ok<>(new FunctionType(parameters, results));
    }

    public String toString() {
        return (
            "FunctionType(\n"
            + (
                "parameters="
                + ToStringUtil.arrayList(this.parameters)
                + "\nresults="
                + ToStringUtil.arrayList(this.results)
            ).indent(2)
            + "\n)"
        );
    }
}
