import '../styles/index.scss';
import "@fontsource/nunito";

import '@popperjs/core';
import 'bootstrap';

import $ from 'jquery';
import {Modal, Collapse, Tooltip} from 'bootstrap';
import axios from "axios";
import {setDefaultOptions, format, addMonths, parse, intervalToDuration, formatDuration, differenceInMonths, differenceInDays} from 'date-fns';
import pl from 'date-fns/locale/pl';
import _ from "lodash";

const api = axios.create({
	withCredentials: true,
})

function wait(time: number): Promise<void> {
	return new Promise((resolve) => {
		setTimeout(() => resolve(), time);
	});
}

$(function () {
	setDefaultOptions({
		locale: pl
	})

	const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
	[...tooltipTriggerList].map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
})

$(function () {
	const $autocompleteBtn = $('#autocomplete-btn');
	const $offerSelect = $('#offer-select');
	const $startDateInput = $('#start-date-input');
	const $endDateInput = $('#end-date-input');
	const $agreementDuration = $('#agreement-duration');

	function getDateFromEl($el: JQuery): Date | undefined {
		const textValue = $el.val();
		if (_.isString(textValue) && !_.isEmpty(textValue)) {
			return parse(textValue, 'yyyy-MM-dd', new Date());
		}
	}

	function calculateDistance(): void {
		const start = getDateFromEl($startDateInput);
		const end = getDateFromEl($endDateInput);
		if (!_.isDate(start) || !_.isDate(end)) {
			$agreementDuration.text('brak danych');
			return;
		}

		const duration = intervalToDuration({start, end});
		$agreementDuration.text(formatDuration({
			months: (duration.years ?? 0) * 12 + (duration.months ?? 0),
			weeks: duration.weeks,
			days: duration.days
		}))
	}

	$startDateInput.on('change', calculateDistance);

	$endDateInput.on('change', calculateDistance);

	$autocompleteBtn.on('click', function () {
		const $option = $offerSelect.find(':selected');
		const offerDuration = $option.data('duration');
		if (!_.isNumber(offerDuration) || _.isNaN(offerDuration)) {
			return;
		}

		const startDate = getDateFromEl($startDateInput) ?? new Date();
		const endDate = addMonths(startDate, offerDuration);

		$startDateInput.val(format(startDate, 'yyyy-MM-dd'));
		$endDateInput.val(format(endDate, 'yyyy-MM-dd'));
		calculateDistance();
	});

	calculateDistance();
})

$(function () {
	$('#page-size-select').on('change', function () {
		const pageSize = $(this).val();
		if (pageSize === null) {
			return;
		}

		const url = new URL(window.location.href);
		url.searchParams.set('pageSize', String(pageSize));
		window.location.assign(url);
	})

	$('#mobile-select').on('change', function () {
		const mobile = $(this).val();
		if (mobile === null) {
			return;
		}

		const url = new URL(window.location.href);
		url.searchParams.set('mobile', String(mobile));
		window.location.assign(url);
	})
});

$(function () {
	const $navbar = $("#navbarNav");
	const navbarEl = $navbar.get(0);
	if (navbarEl === undefined) {
		return;
	}
	const navbar = new Collapse(navbarEl, {
		toggle: false
	});

	let lastWidth = 0;
	$(window).resize(function() {
		const width = $(window).width();
		if (width === undefined) {
			return;
		}

		if (width >= 768) {
			navbar.show();
		} else if (lastWidth >= 768) {
			navbar.hide();
		}
		lastWidth = width;
	});
})

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
			await Promise.all([
				api.post('/offers/create',{ offerId }),
				wait(500)
			]);

			const url = new URL(window.location.href);
			url.pathname = '/user/agreements';
			window.location.assign(url);
		} finally {
			$modalSubmitSpinner.hide();
			$modalSubmit.prop('disabled', false);
		}
	});
});
