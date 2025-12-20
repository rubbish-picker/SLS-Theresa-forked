package Theresa.card.attack;

import Theresa.action.UnseenFutureAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UnseenFuture extends AbstractTheresaCard {
    public static final String ID = "theresa:UnseenFuture";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public UnseenFuture() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 6;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int randomNumber = com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(0, this.damage*2);
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,randomNumber,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new UnseenFutureAction(upgraded));
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
