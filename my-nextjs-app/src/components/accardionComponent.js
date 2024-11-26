"use client";
import { useState } from "react";
import Image from "next/image";
import * as img from "../assets/images";

export default function AccordionComponent({ accordionData }) {
    const [openSections, setOpenSections] = useState({});

    const toggleSection = (section) => {
        setOpenSections((prev) => ({
            ...prev,
            [section]: !prev[section],
        }));
    };

    return (
        <div className="accordion">
            {accordionData.map((section, index) => (
                <div key={index} className="mb-4 px-12 py-1 bg-customColor1 rounded-2xl">
                    <div 
                        className="accordion-header cursor-pointer p-4" 
                        onClick={() => toggleSection(index)}
                    >
                        <h2 className="font-semibold text-xl">{section.title}</h2>
                    </div>
                    {openSections[index] && (
                        <>
                            {Array.isArray(section.content) ? (
                                section.content.map((item, idx) => (
                                    typeof item === 'string' ? (
                                        <div key={idx} className="accordion-content p-4 bg-customColor2 rounded-2xl mt-2 mb-4 font-medium text-customColor7">
                                            <p>{item}</p>
                                        </div>
                                    ) : (
                                        <div key={idx} className="accordion-content p-4 bg-customColor2 rounded-2xl mt-2 mb-4 font-medium">
                                            <div className="flex gap-x-2 mb-2">
                                                <Image src={img.note} width={24} height={24} alt="task"/>
                                                <h3 className="text-customColor7">{item.title}</h3>
                                            </div>
                                            <p className="text-customColor4">{item.description}</p>
                                        </div>
                                    )
                                ))
                            ) : null}
                        </>
                    )}
                </div>
            ))}
        </div>
    );
}
