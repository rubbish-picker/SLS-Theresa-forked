package Theresa.card.attack;

import Theresa.action.DustToPileAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DustStrike extends AbstractTheresaCard {
    public static final String ID = "theresa:DustStrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DustStrike() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.BASIC,CardTarget.ENEMY);
        baseDamage = damage = 3;
        isInnate = true;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        //addToBot(new TheresaAttackAction());
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),attackEffect));
    }

    @Override
    public void triggerWhenLingered() {
        addToBot(new DustToPileAction(this, CardGroup.CardGroupType.HAND));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}

