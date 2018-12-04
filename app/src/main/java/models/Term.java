package models;

import android.support.annotation.NonNull;

public class Term implements Comparable<Term>{
    private String term;
    private String year;

    public Term(String term, String year) {
        this.term = term;
        this.year = year;
    }

    public void setTerm(String term) { this.term = term; }
    public String getTerm() { return this.term; }

    public void setYear(String year) { this.year = year; }
    public String getYear() { return this.year; }

    @Override
    public String toString() {
        return this.getTerm()+"/"+this.getYear();
    }

    @Override
    public int compareTo(@NonNull Term o) {
        return Integer.parseInt(this.getYear()) - Integer.parseInt((o.getYear()));
    }

    @Override
    public boolean equals(Object obj) {
        Term term = (Term) obj;
        return this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.getTerm())+Integer.parseInt(this.getYear());
    }
}
