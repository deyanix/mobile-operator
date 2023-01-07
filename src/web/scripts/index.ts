import '../styles/index.scss';
import "@fontsource/nunito";

import '@popperjs/core';
import 'bootstrap';

import $ from 'jquery';
import {Modal} from 'bootstrap';
import axios from "axios";

interface Offer {
	id: number;
	activationFee: number | null;
	duration: number;
	internetLimit: number;
	monthlyCost: number;
	mobileOffer: MobileOffer | null;
}

interface MobileOffer {
	mmsLimit: number | null;
	mmsRoamingLimit: number | null;
	smsLimit: number | null;
	smsRoamingLimit: number | null;
}

const api = axios.create({
	withCredentials: true,
})

function wait(time: number): Promise<void> {
	return new Promise((resolve) => {
		setTimeout(() => resolve(), time);
	});
}

$(function () {
	const $modalEl = $('#offer-modal');
	const $modalDetails = $('.offer-modal-details', $modalEl);
	const $modalId = $('#offer-modal-id', $modalEl);
	const $modalSubmit = $('.offer-modal-submit', $modalEl);
	const $modalSubmitSpinner = $modalSubmit.find('.spinner-border');
	const modalEl = $modalEl.get(0);
	if (modalEl === undefined) {
		return;
	}

	const modal = new Modal(modalEl);
	$modalSubmitSpinner.hide();

	$('.offer-choose').on('click', function () {
		$modalDetails.empty();
		const $offerCard = $(this).closest('.offer-card')
		const offerId = parseInt($offerCard.data('offer-id'));
		$modalId.val(offerId);
		console.log(offerId)

		$offerCard
			.find('.offer-parameter-name')
			.toArray()
			.map(paramEl => ({
				name: $(paramEl).text().trim(),
				value: $('+ .offer-parameter-value', paramEl).text().replace(/(\r\n|\n|\r|\t)+/gm, " ").trim()
			}))
			.forEach(param =>
				$('<tr>')
					.append($('<th>').text(param.name))
					.append($('<td>').text(param.value))
					.appendTo($modalDetails)
			);

		modal.show();
	});

	$modalSubmit.on('click',async  function () {
		const offerIdText = $modalId.val();
		if (typeof offerIdText !== 'string') {
			return;
		}
		$modalSubmitSpinner.show();
		$modalSubmit.prop('disabled', true);

		const offerId = parseInt(offerIdText);
		try {
			const [response] = await Promise.all([
				api.post('/offers/create',{ offerId }),
				wait(500)
			]);
			console.log(response.data)
		} finally {
			$modalSubmitSpinner.hide();
			$modalSubmit.prop('disabled', false);
		}
	});
});
