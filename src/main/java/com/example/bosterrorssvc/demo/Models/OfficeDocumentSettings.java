package com.example.bosterrorssvc.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDocumentSettings {
  private String TargetScreenSize;
  private String PixelsPerInch;
  private String AllowPNG;
}
