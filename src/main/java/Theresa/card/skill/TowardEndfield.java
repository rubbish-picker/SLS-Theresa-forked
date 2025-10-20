package Theresa.card.skill;

import Theresa.action.DustToPileAction;
import Theresa.action.TowardEndfieldAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TowardEndfield extends AbstractTheresaCard {
    public static final String ID = "theresa:TowardEndfield";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TowardEndfield() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(upgraded){
            addToBot(new DustToPileAction(null, CardGroup.CardGroupType.HAND).setRandom());
        }
        addToBot(new TowardEndfieldAction(2));
    }


    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}




