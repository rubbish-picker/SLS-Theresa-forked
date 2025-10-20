package Theresa.power;

import Theresa.action.DustToPileAction;
import Theresa.helper.StringHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractTheresaPower extends AbstractPower {
    protected PowerStrings powerStrings;
    public static int offset = 0;
    protected boolean offsetID = false;
    protected boolean amountDescription = false;

    public AbstractTheresaPower(String ID, AbstractCreature owner, int amount) {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
        this.name = powerStrings.NAME;
        this.ID = ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        String tmpID = ID.replace(StringHelper.MOD_ID,"");
        String path128 = "TheresaResources/img/powers/" + tmpID + "_84.png";
        String path48 = "TheresaResources/img/powers/" + tmpID + "_32.png";
        if(Gdx.files.internal(path128).exists() && Gdx.files.internal(path48).exists()) {
            this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        }
        else {
            this.loadRegion("lockon");
        }
        this.type = PowerType.BUFF;
    }

    public AbstractTheresaPower(String ID, AbstractCreature owner) {
        this(ID, owner, 0);
    }

    public AbstractTheresaPower setOffsetID(){
        offsetID = true;
        offset++;
        this.ID+=offset;
        return this;
    }

    public AbstractTheresaPower setAmountDescription(){
        amountDescription = true;
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        return this;
    }

    //PS:越低越靠前
    public AbstractTheresaPower setPriority(int priority){
        this.priority = priority;
        return this;
    }

    public AbstractTheresaPower setDebuff(){
        this.type = PowerType.DEBUFF;
        return this;
    }

    public void triggerOnBecomeDust(AbstractCard c){}

    @Override
    public void updateDescription() {
        if(!amountDescription)
            this.description = powerStrings.DESCRIPTIONS[0];
        else
            this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
}
