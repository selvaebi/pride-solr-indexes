package uk.ac.ebi.pride.solr.indexes.pride.model;

public class DataPair implements Comparable{

    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public DataPair(int count,String name){
        this.count = count;
        this.name = name;
    }

    @Override
    public int compareTo(Object O) {
        DataPair obj = (DataPair)O;
        return (this.count-obj.count)>0?-1:1;
    }

    @Override
    public String toString() {
        return name+":"+count;
    }

    @Override
    public boolean equals(Object O) {
        DataPair obj = (DataPair)O;
        return name==obj.name;
    }
}