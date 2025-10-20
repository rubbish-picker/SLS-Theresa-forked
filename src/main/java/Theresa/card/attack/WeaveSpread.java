package Theresa.card.attack;

import Theresa.action.SpreadAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WeaveSpread extends AbstractTheresaCard {
    public static final String ID = "theresa:WeaveSpread";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public WeaveSpread() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 15;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),attackEffect));
        addToBot(new SpreadAction(CardGroup.CardGroupType.DRAW_PILE));
        addToBot(new SpreadAction(CardGroup.CardGroupType.DISCARD_PILE));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }
}


