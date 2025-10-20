package Theresa.card.skill;

import Theresa.action.ChooseDustToPileAction;
import Theresa.action.DustAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Candle extends AbstractTheresaCard {
    public static final String ID = "theresa:Candle";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Candle() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ChooseDustToPileAction(CardGroup.CardGroupType.EXHAUST_PILE,1));
        addToBot(new DustAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}



