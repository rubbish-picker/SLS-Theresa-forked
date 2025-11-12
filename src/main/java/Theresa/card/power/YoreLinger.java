package Theresa.card.power;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.YoreLingerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YoreLinger extends AbstractTheresaCard {
    public static final String ID = "theresa:YoreLinger";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public YoreLinger() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new YoreLingerPower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}





