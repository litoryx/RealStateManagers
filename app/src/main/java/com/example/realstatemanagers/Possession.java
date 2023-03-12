package com.example.realstatemanagers;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.realstatemanagers.model.Agent;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Possession implements Parcelable {

    @NonNull
    @PrimaryKey
    String id;
    String desc;
    String type_bien;
    String val_bien;
    String surface;
    String nbre_piece;
    String phto;
    String adr;
    String PI_proximite;
    String statut;
    String date_begin;
    String date_send;
    String agent;

    public Possession(String id, String desc, String type_bien, String val_bien, String surface, String nbre_piece, String phto, String adr, String PI_proximite, String statut, String date_begin, String date_send, String agent) {
        this.id = id;
        this.desc = desc;
        this.type_bien = type_bien;
        this.val_bien = val_bien;
        this.surface = surface;
        this.nbre_piece = nbre_piece;
        this.phto = phto;
        this.adr = adr;
        this.PI_proximite = PI_proximite;
        this.statut = statut;
        this.date_begin = date_begin;
        this.date_send = date_send;
        this.agent = agent;
    }

    public Possession(Parcel in) {
        id = in.readString();
        desc = in.readString();
        type_bien = in.readString();
        val_bien = in.readString();
        surface = in.readString();
        nbre_piece = in.readString();
        phto = in.readString();
        adr = in.readString();
        PI_proximite = in.readString();
        statut = in.readString();
        date_begin = in.readString();
        date_send = in.readString();
        agent = in.readString();
    }

    public static final Creator<Possession> CREATOR = new Creator<Possession>() {
        @Override
        public Possession createFromParcel(Parcel in) {
            return new Possession(in);
        }

        @Override
        public Possession[] newArray(int size) {
            return new Possession[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getType_bien() {

       if(type_bien != null){ return type_bien;}else{return null;}
    }

    public String getVal_bien() {
        return val_bien;
    }

    public String getSurface() {
        return surface;
    }

    public String getNbre_piece() {
        return nbre_piece;
    }

    public String getPhto() {
        return phto;
    }

    public String getAdr() {
        return adr;
    }

    public String getPI_proximite() {
        return PI_proximite;
    }

    public String getStatut() {
        return statut;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public String getDate_send() {
        return date_send;
    }

    public String getAgent() {
        return agent;
    }

    public static Possession fromContentValues(ContentValues values){

        Possession possession = new Possession(values.getAsString("text"),values.getAsString("text"),values.getAsString("text"),
                values.getAsString("text"),values.getAsString("text"),values.getAsString("text"),values.getAsString("text"),
                values.getAsString("text"),values.getAsString("text"),values.getAsString("text"),values.getAsString("text"),values.getAsString("text"),values.getAsString("text"));

        return possession;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(desc);
        parcel.writeString(type_bien);
        parcel.writeString(val_bien);
        parcel.writeString(surface);
        parcel.writeString(nbre_piece);
        parcel.writeString(phto);
        parcel.writeString(adr);
        parcel.writeString(PI_proximite);
        parcel.writeString(statut);
        parcel.writeString(date_begin);
        parcel.writeString(date_send);
        parcel.writeString(agent);
    }
}
