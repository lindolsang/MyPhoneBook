package kr.lindol.android.myphonebook.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.lindol.android.myphonebook.base.CallState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyIntentServicePresenterTest {

    private MyIntentServiceContract.View mView;

    @Mock
    private PhoneNumberModel mModel;

    @InjectMocks
    private MyIntentServicePresenter mPresenter;

    @Before
    public void setUp() {
        mView = mock(MyIntentServiceContract.View.class);
        mPresenter = new MyIntentServicePresenter(mView);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleIncomingCallWithRegisteredPhoneNumberThenReturnShowSummary() {

        when(mModel.contains("01011112222")).thenReturn(true);
        when(mModel.get("01011112222")).thenReturn("Kim");

        mPresenter.handleIncomingCall("01011112222", CallState.RINGING.getState());

        assertEquals(mModel.hashCode(), mPresenter.getModel().hashCode());

        verify(mView, times(1))
                .showSummary("01011112222", "Kim");
    }

    @Test
    public void handleIncomingCallWithRegisteredPhoneNumberThenReturnCloseSummary() {

        when(mModel.contains("01011112222")).thenReturn(true);
        when(mModel.get("01011112222")).thenReturn("Kim");

        mPresenter.handleIncomingCall("01011112222", CallState.OFFHOOK.getState());

        verify(mView, times(1))
                .closeSummary();
    }

    @Test
    public void handleIncomingCallWithNotRegisteredPhoneNumberThenReturnShowNotFoundPhoneNumberView() {

        when(mModel.contains(any(String.class))).thenReturn(false);

        mPresenter.handleIncomingCall("01011112222", CallState.RINGING.getState());

        verify(mView, times(1))
                .showErrorForNotFoundPhoneNumber("01011112222");
    }
}