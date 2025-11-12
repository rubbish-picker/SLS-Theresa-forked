package Theresa.save;

import Theresa.helper.CivilightHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class TheresaSave {

    ArrayList<CardSave> cardSaves;


    public void onSave(){
        cardSaves = new ArrayList<>();
        for(AbstractCard c: CivilightHelper.cardSaveList){
            CardSave save = CardSave.toSave(c);
            if(save!=null){
                cardSaves.add(save);
            }
        }
    }

    public void onLoad(){
        if(cardSaves!=null){
            CivilightHelper.cardSaveList.clear();
            for(CardSave cardSave:cardSaves){
                try {
                    CivilightHelper.cardSaveList.add(CardSave.toCard(cardSave));
                }
                catch (Exception e){
                    //haha
                }
            }
        }
    }

    public void onDelete(){
        CivilightHelper.cardSaveList.clear();
    }
}
